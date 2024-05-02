**Mobile Development 2023/24 Portfolio**
# Retrospective

Student ID: `22083120`

The app mostly achieved its goals. I adjusted the budget input to allow users to add income/expenses by category rather than by bills and general payments as previously defined. 

Therefore, this customization also lets users tailor the app to their financial situation by logging their data and setting a general financial goal, but not specific objectives such as separate savings for a car. The focus is on highlighting expenses and not detailed goals, with the information presented by category, the percentage spent over income, and a graph tracking income variation, geared towards university students. During the development, I faced difficulties with the Firebase plug-in and defining the login with password and email, therefore having access to the home page with no login, which was rectified by adapting UserLoginActivity with a redirect. 

For future projects, I'd implement MVVM architecture for News API articles, ensuring a clear separation of concerns. If given more time, I would integrate a spending calendar (to track daily spending) and refine the goal-setting feature to track progress continuously with a new table in the room database. Exploring integration with banking apps via implicit intents is also a priority, with a focus on maintaining security - so the expenses would appear automatically.