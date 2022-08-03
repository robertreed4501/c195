# c195

This application, "Client Scheduler", is intended to keep track of Appointments and allows the user
to create, edit, and delete appointments and customer records.  Users can also view reports such as
the number of appointments by type and month, each contact's next months' schedule, and a pie chart
representing the number of appointments by customer.



This program was developed with IntelliJ/IDEA 20.1.1 using Java jdk-11.0.11
JavaFX version openJFX 11.0.2 and MySQL JDBC Connector version 8.0.25
<br/>
<div align="center">
  <img src="https://github.com/robertreed4501/robertreed4501/blob/main/images/screenshots/customersPage.PNG" height="420px"><br/>
  <img src="https://github.com/robertreed4501/robertreed4501/blob/main/images/screenshots/appointmentsPage.PNG" height="420px"><br/>
  <img src="https://github.com/robertreed4501/robertreed4501/blob/main/images/screenshots/reportsPage.PNG" height="420px"><br/>
</div>
<br/>

Instructions for use:
Launch the application.  To login as a test user enter 'test' in both the username and password fields.  Click Login.
A popup will inform you whether user 'test' has any appointments within the next 15 minutes or not.  Press OK.
The screen now shows a list of Customers on the customer tab.  At the top of the screen are the tabs.  To reach the
appointment or report pages, click on the corresponding tab.  When on either the customer or appointment page, clicking
on a record in the table will populate the fields below.  From here you can edit any of the editable fields and by
clicking 'Update', the record will update, or by clicking 'Delete', the record will be deleted.  If deleting a customer,
the customer must have no appointments currently scheduled.  To create a new record, press '+Create New Customer' or
'+Create New Appointment'.  This will generate a new ID number for the appointment or customer. Fill out the fields
completely and press 'Save' to save the new record.  Pressing the exit button will exit the application and any unsaved
changes will be lost.  On the reports tab, choose a Contact to view their next months' schedule.  Below that, choose
a month to view the number of each type of appointment scheduled in that month.  Finally, the Appointments by Customer
chart shows the distribution of customers in all appointments.



