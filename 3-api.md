
# API description



Focusing on the transaction functionality of my app, where the user can add income and expenses. I chose to use ConstraintLayout for the activity_add_transaction XMl from the start, as I knew I would be expanding/adding different input fields and developing new processes that depended on this form (such as the type dropdown). Therefore, ensuring the UI would be adaptable, and centered regardless of the size of the phone and the number of fields was a priority in improving the maintainability of the UI. Despite the possible use of LinearLayot or RelativeLayout for the application presented, the ConstraintLayout was the optimal choice during development considering the possible expanding elements that need to be positioned. Hence, adjusting them dynamically, allows each input field and button to be aligned and distributed relative to each other and the parent. Especially for the close button to stay at the top-right corner, for example, providing flexible positioning across different devices.

Furthermore, View API  in the DetailedActivity class played a crucial role in implementing the UI logic and enabling user interaction. By having views such as "updated" and on-click listeners attached to trigger the update process, I was able to specify the behavior associated with the View and control if they were available for the user or not. Given I had few numbers of elements, View was more straightforward than setting up ViewModel and providing better readability when revisiting my code for other functionalities. 

On this same topic, when handling persistent data with my Room application, I used "@Query" annotations to interact with the transactions table and fetch data to keep database-related logic separate using Dao. Also, processed data in memory through Kotlin functions, which could have been done with Dao. However, this method offered flexibility for more complex data manipulations as I was doing percentage calculations with the values. 
