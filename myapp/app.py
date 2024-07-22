from flask import Flask, render_template, request, redirect, url_for, session
import re

app = Flask(__name__)
app.secret_key = 'your_secret_key'

# Load common passwords
with open('10_million_password_list.txt') as f:
    common_passwords = set(f.read().splitlines())

# OWASP Proactive Controls C6: Enforce password complexity
password_pattern = re.compile(r'^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\W]).{8,}$')

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        password = request.form['password']
        if password in common_passwords:
            error = 'Common password detected. Please use a different password.'
            return render_template('index.html', error=error)
        elif not password_pattern.match(password):
            error = 'Password does not meet complexity requirements.'
            return render_template('index.html', error=error)
        else:
            session['password'] = password
            return redirect(url_for('welcome'))
    return render_template('index.html')

@app.route('/welcome')
def welcome():
    if 'password' not in session:
        return redirect(url_for('index'))
    return render_template('welcome.html', password=session['password'])

@app.route('/logout')
def logout():
    session.pop('password', None)
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)
