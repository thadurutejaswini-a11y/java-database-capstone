# User Story Template

## 1. Admin Login
*Title:*
As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely.

*Acceptance Criteria:*
1. [User can enter a valid username and password]
2. [System authenticates credentials and redirects to the admin dashboard]
3. [System displays an error message for invalid credentials]

*Priority:* High
*Story Points:* 2
*Notes:*
- Password input must be masked.

---

## 2. Admin Logout
*Title:*
As an admin, I want to log out of the portal, so that I can protect system access.

*Acceptance Criteria:*
1. [Logged-in user can click a Logout button]
2. [System destroys the active session]
3. [User is redirected back to the login page]

*Priority:* High
*Story Points:* 1
*Notes:*
- None

---

## 3. Add Doctors
*Title:*
As an admin, I want to add doctors to the portal, so that they can be available to patients.

*Acceptance Criteria:*
1. [Admin can access a form to input doctor details like Name and Specialization]
2. [System validates that required fields are not empty]
3. [System saves the doctor profile and displays a success confirmation]

*Priority:* Medium
*Story Points:* 3
*Notes:*
- Email addresses must be unique.

---

## 4. Delete Doctor Profile
*Title:*
As an admin, I want to delete a doctor's profile from the portal, so that inactive doctors are removed.

*Acceptance Criteria:*
1. [Admin can select a doctor profile and click a Delete button]
2. [System displays a confirmation pop-up before permanent deletion]
3. [Upon confirmation, the profile is removed or marked as inactive]

*Priority:* Medium
*Story Points:* 2
*Notes:*
- Check if the doctor has pending appointments before deleting.

---

## 5. Track Usage Statistics
*Title:*
As an admin, I want to run a stored procedure in MySQL CLI, so that I can get the number of appointments per month and track usage statistics.

*Acceptance Criteria:*
1. [Executing the specific stored procedure via MySQL CLI returns a structured table]
2. [The output accurately counts and groups appointments by month]
3. [The query runs efficiently without locking tables]

*Priority:* Low
*Story Points:* 3
*Notes:*
- Stored procedure must accept parameters for filtering years.
