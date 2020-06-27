								

## **Term Project Explanation**

> I should mention that Settings, Help and vibration is not part of original proposal. These are extra feature I just thought of adding for testing purposes

***SplashScreen.java***: This is the first activity that sows up when activity is launched. Also some what animation is used in this activity.

***MainActivity.java***: This is the main activity that is shown to user after splashscreen and it has a floating action button that allows user to add a new reminder.
It also has a menu bar in which *Add Reminder , Settings , and Help* items are available. **FAB** and **Add Reminder** both open a new activity, **Settings** also open a new activity and **Help** doesn't do anything since it wasn't originally part of proposal it is just an extra option.
MainActivity is also responsible for showing all the saved reminders and when you click one of those a new activity which is ***Edit Reminder*** is opened which allows you to edit reminder details and delete as well.

***Add Reminder/Edit Reminder:***: It allows you to create a new reminder providing 

 - Title
 - Date
 - Time 
 - Repeat (Not part of proposal)
 - Repeat No (Not part of proposal)
 - Repeat Interval (Not part of proposal)
for the reminder
It only has **Save Reminder** Option when opened for **Add Reminder** but it has a **Delete Reminder** button when opened up as **Edit Reminder** .

***ERSettings.java***: Allows you to store your name and email address. It also provides an option for vibration that needs to be tested on mobile phone since it can't be tested on laptop but it doesn't throw an exception. 

***Working***: When a reminder is set it will go off at the mentioned time and a notification will show up as well an alarm tone will be played. There is a button in notification that dismisses the notification and stops alarm tone. If notification is clicked **Edit Reminder** will open and alarm tone will be stopped and you can edit that particular reminder.
 

 