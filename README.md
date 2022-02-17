# Research center
# Project overview
Research center provides an opportynity for clients to rent equipment of various types and doing their research with help of assistant or without.
There are different departments in center. Every deparment has laboratories with similar focus of reseearch.
Client see through the equipment list of laboratory and book research time ot selected equipment. The equipment list and assistants are moderated by laboratory manager.
After choosing appropriate time, client can pay his order or cancel it.
In turn, the assistant can see his schedule, when and what equipment needs his assistance.
Also, there is a role of the administrator who is responsible for adding new department/laboratory/laboratory manager/equipment type, and some user management options, such as block/unclock user, delete user.

## Roles
### Guest available funtionality:
+ Look through the research center departments, laboratories, laboratory managers, equipment list
+ Find equipment by type
+ See depaprtment/laboratory/manager details
+ Change locale
+ Login/register
+ Confirm registration with email

### Client available funtionality (in additional to guests' posibilities):
+ Rescharge balance
+ Check balance
+ Get equipment timetable on selected date (only for active equipment and working days)  
+ Rent equipment at necessary time (with or without an assistant; if there are no available assistants and equipment requires an assistant client can't choose this research time interval)
+ Check client orders (only orders with state BOOKED or PAYED)
+ Pay order (only if order state BOOKED)
+ Cancel order (only if order state BOOKED)
+ See order details (laboratory, equipment, assistant if present)
+ Edit profile (firsname, lastname, phone)
+ Change password

### Assistant available funtionality (in additional to guests' posibilities):
+ See assistant schedule (schedule builds only for payed orders where assistant is necessary)
+ Edit profile (firsname, lastname, phone)
+ Change password
+ Upload avatar

### Manager available funtionality (in additional to guests' posibilities):
+ Managers laboratory details (view all laboratory equipment list and assistants)
+ Edit laboratory name/description/location
+ Add laboratory image
+ Add assistant to laboratory (same as register new user, without confirm registration)
+ Add new equipment to laboratory
+ Edit selected equipment (add image, set average research time, set price per hour, edit name/description, set equipment state - ACTIVE/INACTIVE)
+ Edit profile (firsname, lastname, phone)
+ Change password
+ Upload avatar
+ Edit manager description
+ See orders that are carried out in the laboratory (orders state BOOKED/PAYED/COMPLETED)
+ Complete order (only for PAYED order and if current time is after the end of research)
+ See order details (laboratory, equipment, assistant, and client contacts) 

### Admin available funtionality (in additional to guests' posibilities):
+ Add new admin
+ Add new department
+ Add new laboratory (only in existing department)
+ Add new manager (only in laboratory without manager)
+ Add new equipment type (e.g. optical/x-ray/microscopes etc)
+ Edit profile (firsname, lastname, phone)
+ Change password
+ See all users
+ Block/unblock selected user
+ Delete selected user
+ Can't delete or block self

### Database scheme
<p align="center">
  <img src="https://github.com/Lazavenka/finalproject/blob/master/schema.png" width="1000" title="hover text">
</p>
