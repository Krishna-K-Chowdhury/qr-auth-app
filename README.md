# QR-Authentication System

The QR-Authentication System is a secure and efficient solution that allows authenticated admins to generate unique QR codes for each person in the event list. These QR codes are then sent via email to respective users. Upon scanning the QR code using the Android app, the backend verifies its authenticity against the MySQL database and marks the QR code as "visited," ensuring that the same QR code cannot be used again.

## Features

- **Admin Authentication**: The Android app includes a secure login system that allows only authorized administrators to access the QR code authentication functionalities.

- **Generate Unique QR Codes**: Admins run a Python script on their local machine to generate unique QR codes for each person in the Excel spreadsheet. The script communicates with the backend server to send QR codes to the respective email addresses.

- **QR Code Authentication**: After scanning the QR code using the Android app, the backend server verifies its authenticity against the MySQL database.

- **Prevent QR Code Reuse**: Once a QR code is successfully authenticated, it is marked as "visited" in the MySQL database, making it impossible to use the same QR code again.


## Prerequisites

- **Python**: Ensure you have Python installed on your system. The script uses packages like `pandas`, `qrcode`, and `smtplib`, which you can install using `pip`.

- **Android App**: Install the Android app on your device and use the secure login credentials provided for administrators.

- **MySQL Database**: Set up a MySQL database to store QR code authentication logs, ensuring each QR code's uniqueness and marking them as "visited."

## Usage

1. **Clone the repository to your local machine.**

2. **Update Configuration**: In the Python script (`qr_code_gen_send.py`), modify the following variables:

   - `file_path`: Set the path to your Excel spreadsheet.
   - `smtp_server`, `smtp_port`, `sender_email`, `sender_password`: Configure your SMTP email server details.
   - `your_smtp_server`: Replace with your actual SMTP server details.
   - `your_sender_email`: Replace with your email address used for sending QR codes.
   - `your_sender_password`: Replace with the password for the sender email account.

3. **Install Dependencies**: Run `pip install pandas qrcode smtplib` to install the required Python packages.

4. **Run the Python Script**: Admins should execute the Python script (`qr_code_gen_send.py`) on their local machine to generate unique QR codes, send them via email, and introduce a delay to avoid email blocking.

5. **Android App**: Use the secure login credentials provided to access the Android app as an administrator. Scan QR codes using the app for authentication purposes.

6. **Backend Integration**: Integrate the Android app with the backend server to scan QR codes and authenticate them. Ensure the backend server communicates with the MySQL database to verify and mark QR codes as "visited" after successful authentication.


## Disclaimer

This QR-Authentication System is provided as-is without any warranty. Use it at your own risk.

---
