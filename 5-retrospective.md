**Mobile Development 2023/24 Portfolio**
# Retrospective

Student ID: `22083120`

The app has largely accomplished its objectives set in the overview. However, I adjusted the budget input to allow users to add income/expenses by category rather than by bills and general payments as previously defined. Therefore, this customization also lets users tailor the app to their financial situation by logging their data and setting a general goal, but not specific objectives such as separate savings for a car. The emphasis is on highlighting expenses, with the information presented by category, the percentage spent over income, and a graph tracking income variation. During development, I faced difficulties with the Firebase plug-in and defining the login with password and email, thus having access to the home page with no login, which was rectified by adapting UserLoginActivity with a redirect in a new function. 

For future projects, I'd implement MVVM architecture for News API articles, ensuring a clear separation of concerns. If given more time, I would integrate a spending calendar (to track daily expenses) and refine the goal-setting feature to track progress continuously with a new table in the room database. Exploring integration with banking apps via implicit intents would also be a preference, with a focus on maintaining security - expenses would appear automatically.