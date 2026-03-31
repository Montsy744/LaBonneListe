# 🚀 Mini Starter Full Stack (Servlet + React + PostgreSQL)

## 🧱 1. Model (Task.java)

```java
public class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private int userId;

    public Task() {}

    public Task(int id, String title, String description, String status, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    // getters & setters
}
```

---

## 🔌 2. Connexion DB (JDBC)

```java
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/tondb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

---

## 🗄️ 3. DAO (TaskDAO.java)

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM task";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getInt("user_id")
                );
                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void createTask(Task task) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO task(title, description, status, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getUserId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## 🌐 4. Servlet (TaskServlet.java)

```java
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/tasks")
public class TaskServlet extends HttpServlet {

    private TaskDAO taskDAO = new TaskDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks = taskDAO.getAllTasks();

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(tasks));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Task task = gson.fromJson(reader, Task.class);

        taskDAO.createTask(task);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}
```

---

## ⚛️ 5. Front React

### Récupérer les tâches

```javascript
import { useEffect, useState } from "react";

function App() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/tasks")
      .then(res => res.json())
      .then(data => setTasks(data));
  }, []);

  return (
    <div>
      <h1>Tasks</h1>
      {tasks.map(task => (
        <div key={task.id}>
          <h3>{task.title}</h3>
          <p>{task.description}</p>
        </div>
      ))}
    </div>
  );
}

export default App;
```

---

### Ajouter une tâche

```javascript
function addTask() {
  fetch("http://localhost:8080/api/tasks", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      title: "Nouvelle tâche",
      description: "Test",
      status: "TODO",
      userId: 1
    })
  }).then(() => window.location.reload());
}
```

---

## 🗄️ 6. SQL

```sql
CREATE TABLE "user" (
  id SERIAL PRIMARY KEY,
  email VARCHAR(100),
  password VARCHAR(255)
);

CREATE TABLE task (
  id SERIAL PRIMARY KEY,
  title VARCHAR(100),
  description TEXT,
  status VARCHAR(20),
  user_id INT REFERENCES "user"(id)
);
```
