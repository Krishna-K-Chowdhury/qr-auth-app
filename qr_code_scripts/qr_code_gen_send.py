import pandas as pd
import qrcode
import smtplib
import time
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage

# Function to generate QR code
def generate_qr_code(data, file_name):
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(data)
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white")
    img.save(file_name)

# Read the Excel file
file_path = 'your_spreadsheet.xlsx'
df = pd.read_excel(file_path)

# SMTP Email Configuration
smtp_server = 'your_smtp_server'
smtp_port = 587
sender_email = 'your_sender_email'
sender_password = 'your_sender_password'

# Iterate through the rows, generate QR code, and send emails
for index, row in df.iterrows():
    email = row['email']
    qrx_value = str(row['qrx'])

    # Generate the QR code
    qr_file_name = f'QR_{qrx_value}.png'
    generate_qr_code(qrx_value, qr_file_name)

    # Send email with the QR code
    msg = MIMEMultipart()
    msg['From'] = sender_email
    msg['To'] = email
    msg['Subject'] = 'Your QR Code'

    # Attach the QR code image
    with open(qr_file_name, 'rb') as f:
        img = MIMEImage(f.read())
        img.add_header('Content-ID', '<qr_code>')
        msg.attach(img)

    # Send the email
    server = smtplib.SMTP(smtp_server, smtp_port)
    server.starttls()
    server.login(sender_email, sender_password)
    server.sendmail(sender_email, email, msg.as_string())
    server.quit()

    # Delete the generated QR code image after sending the email
    import os
    os.remove(qr_file_name)

    # Add a delay of 5 seconds
    time.sleep(5)

print("QR codes sent to respective emails successfully!")
