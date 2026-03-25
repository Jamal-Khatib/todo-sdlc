# Todo App Project Plan

This document outlines the project plan, user stories, backlog, and sprint breakdown for the Todo App MVP based on the finalized requirements.

## 1. Stories & Gherkin Acceptance Criteria

---

### Epic: Task CRUD

**Story: Create a Task**
> As a user, I want to create a new task with a title and optional details, so that I can capture what I need to do.

```gherkin
Scenario: Successfully create a task with required and optional fields
  Given I am on the main Todo page
  When I enter "Buy groceries" in the title field
  And I enter "Milk, bread, and eggs" in the description field
  And I select a due date of "2027-12-25"
  And I set the priority to "High"
  And I add the tags "home" and "shopping"
  And I submit the new task form
  Then I should see the new task "Buy groceries" in my task list
  And the task should show the priority "High"
  And the task should have the tags "home" and "shopping"
```

**Story: View Task Details**
> As a user, I want to view all the details of a single task, so I can see the full description and other metadata.

```gherkin
Scenario: View the details of an existing task
  Given a task exists with the title "Plan vacation" and description "Research flights and hotels for a trip to Hawaii."
  When I click on the task "Plan vacation" in the list
  Then I should see a detailed view of the task
  And the view should display the title "Plan vacation"
  And the view should display the full description "Research flights and hotels for a trip to Hawaii."
```

**Story: Update a Task**
> As a user, I want to edit the details of a task, so that I can keep its information current.

```gherkin
Scenario: Modify the priority of a task
  Given a task exists with the title "Finish report" and priority "Medium"
  When I open the task "Finish report" for editing
  And I change its priority to "High"
  And I save the changes
  Then the task "Finish report" in the list should now show a priority of "High"
```

**Story: Mark a Task as Complete**
> As a user, I want to mark a task as complete, so that I can track my progress.

```gherkin
Scenario: Toggle a task's completion status
  Given a task exists with the title "Water the plants" and it is not complete
  When I check the completion box for the task "Water the plants"
  Then the task "Water the plants" should be marked as complete
  And when I uncheck the completion box for the task "Water the plants"
  Then the task "Water the plants" should be marked as not complete
```

**Story: Soft Delete a Task**
> As a user, I want to delete a task I no longer need, so that it doesn't clutter my list.

```gherkin
Scenario: A user deletes a task
  Given a task with the title "Old task" exists in my task list
  When I click the delete button for the task "Old task"
  And I confirm the deletion
  Then the task "Old task" should no longer be visible in my task list
```

---

### Epic: Task Viewing & Filtering

**Story: Search for Tasks**
> As a user, I want to search for tasks by keyword, so I can quickly find a specific item.

```gherkin
Scenario: Search for a task by a keyword in its title
  Given a task exists with the title "Prepare presentation slides"
  And another task exists with the title "Review quarterly budget"
  When I enter "presentation" into the search bar
  Then I should see the task "Prepare presentation slides" in the results
  And I should not see the task "Review quarterly budget" in the results
```

**Story: Filter Tasks by Priority**
> As a user, I want to filter my tasks by priority, so I can focus on what's most important.

```gherkin
Scenario: Filter tasks to show only high-priority items
  Given a task "Urgent task" exists with priority "High"
  And a task "Normal task" exists with priority "Medium"
  When I apply the "High" priority filter
  Then I should see the task "Urgent task" in the list
  And I should not see the task "Normal task" in the list
```

**Story: Filter Tasks by Tag**
> As a user, I want to filter my tasks by a specific tag, so I can find related work.

```gherkin
Scenario: Filter tasks by a single tag
  Given a task "Design mockups" exists with the tag "project-phoenix"
  And a task "Code review" exists with the tag "project-apollo"
  When I apply the filter for the tag "project-phoenix"
  Then I should see the task "Design mockups" in the list
  And I should not see the task "Code review" in the list
```

**Story: Sort the Task List**
> As a user, I want to sort my task list by due date, priority, or creation date, so I can organize it in a way that makes sense to me.

```gherkin
Scenario: Sort tasks by priority in descending order
  Given a task "Task A" exists with priority "High"
  And a task "Task B" exists with priority "Low"
  And a task "Task C" exists with priority "Medium"
  When I sort the task list by "Priority" in "Descending" order
  Then the tasks should appear in the order: "Task A", "Task C", "Task B"
```

**Story: Paginate the Task List**
> As a user with many tasks, I want to see my tasks split into pages, so the app remains fast and easy to navigate.

```gherkin
Scenario: Navigate to the second page of tasks
  Given there are 30 tasks in my list
  And the page size is set to 20
  When I am on the first page of the task list
  And I click the "Next" page button
  Then I should see the remaining 10 tasks on the second page
```

---

### Epic: UI/UX & NFRs

**Story: Responsive UI**
> As a user, I want the application to be fully usable on my mobile device, so I can manage my tasks on the go.

```gherkin
Scenario: View the task list on a mobile device
  Given I am using a device with a screen width of 360px
  When I open the Todo app
  Then the layout should adjust to a single-column view
  And all text should be readable and interactive elements should be tappable
```

**Story: Accessible UI**
> As a user with visual impairments, I want to be able to navigate and use the application with a screen reader and keyboard, so that I can manage my tasks effectively.

```gherkin
Scenario: Navigate the app using only the keyboard
  Given I am on the main Todo page
  When I press the "Tab" key repeatedly
  Then the focus should move logically through all interactive elements (inputs, buttons, links)
  And the currently focused element should have a visible focus indicator
```

## 2. Product Backlog

| ID    | Story                          | Epic                       | Priority | Estimate (No AI) | Estimate (With AI) | Dependencies |
|-------|--------------------------------|----------------------------|----------|------------------|--------------------|--------------|
| **BE-1**  | Setup Backend & DB Schema      | Task CRUD                  | High     | M                | S                  | -            |
| **BE-2**  | Implement Create Task API      | Task CRUD                  | High     | M                | S                  | BE-1         |
| **BE-3**  | Implement Read/List Task API   | Task CRUD                  | High     | M                | S                  | BE-1         |
| **BE-4**  | Implement Update Task API      | Task CRUD                  | High     | M                | S                  | BE-1         |
| **BE-5**  | Implement Soft Delete Task API | Task CRUD                  | High     | S                | XS                 | BE-1         |
| **FE-1**  | Create Basic UI Shell & Form   | UI/UX                      | High     | M                | S                  | -            |
| **FE-2**  | Connect UI to Create Task API  | Task CRUD                  | High     | S                | XS                 | BE-2, FE-1   |
| **FE-3**  | Display List of Tasks in UI    | Task CRUD                  | High     | M                | S                  | BE-3, FE-1   |
| **FE-4**  | Connect UI to Update/Delete    | Task CRUD                  | High     | M                | S                  | BE-4, BE-5, FE-3 |
| **BE-6**  | Add API Sorting Logic          | Task Viewing & Filtering   | Medium   | S                | XS                 | BE-3         |
| **BE-7**  | Add API Filtering Logic        | Task Viewing & Filtering   | Medium   | M                | S                  | BE-3         |
| **BE-8**  | Add API Search Logic           | Task Viewing & Filtering   | Medium   | M                | S                  | BE-3         |
| **BE-9**  | Add API Pagination Logic       | Task Viewing & Filtering   | Medium   | S                | XS                 | BE-3         |
| **FE-5**  | Implement UI Sorting Controls  | Task Viewing & Filtering   | Medium   | S                | XS                 | BE-6, FE-3   |
| **FE-6**  | Implement UI Filter Controls   | Task Viewing & Filtering   | Medium   | M                | S                  | BE-7, FE-3   |
| **FE-7**  | Implement UI Search Input      | Task Viewing & Filtering   | Medium   | S                | XS                 | BE-8, FE-3   |
| **NFR-1** | Implement Responsive UI        | UI/UX                      | Low      | M                | S                  | FE-1         |
| **NFR-2** | Implement Accessibility Checks | UI/UX                      | Low      | L                | M                  | FE-1         |

## 3. Sprint Plan

### Sprint 1 (Weeks 1-2)

*Goal: Deliver core CRUD functionality. A user can create, view, update, and delete tasks via the UI.* 

- **BE-1:** Setup Backend & DB Schema
- **BE-2:** Implement Create Task API
- **BE-3:** Implement Read/List Task API (basic version, no sort/filter)
- **BE-4:** Implement Update Task API
- **BE-5:** Implement Soft Delete Task API
- **FE-1:** Create Basic UI Shell & Form
- **FE-2:** Connect UI to Create Task API
- **FE-3:** Display List of Tasks in UI
- **FE-4:** Connect UI to Update/Delete

### Sprint 2 (Weeks 3-4)

*Goal: Enhance the task list with searching, sorting, filtering, and pagination. Address key non-functional requirements.* 

- **BE-6:** Add API Sorting Logic
- **BE-7:** Add API Filtering Logic (Priority & Tag)
- **BE-8:** Add API Search Logic
- **BE-9:** Add API Pagination Logic
- **FE-5:** Implement UI Sorting Controls
- **FE-6:** Implement UI Filter Controls
- **FE-7:** Implement UI Search Input
- **NFR-1:** Implement Responsive UI for Mobile
- **NFR-2:** Implement Accessibility Checks

## 4. Risks & Assumptions

### Assumptions
- **Team Familiarity:** Assumes the development team is proficient with the specified tech stack (Spring Boot, Thymeleaf, vanilla JS).
- **Stable Requirements:** Assumes the MVP scope is locked and will not change significantly during the two sprints.
- **Environment Availability:** Assumes that development, testing, and deployment environments will be available and configured from the start.
- **AI Tooling:** The reduced estimates assume access to and proficiency with AI code assistants that can generate boilerplate code, write tests, and suggest implementations based on the requirements.

### Risks
- **Scope Creep:** Stakeholders might request features from the "Out of Scope" list (e.g., authentication) to be included in the MVP, jeopardizing the timeline.
- **Technical Debt:** The choice of SQLite for production, while acceptable for the MVP, is a known risk and will require a migration effort (to Postgres) post-MVP. This must be tracked.
- **UI/UX Complexity:** "Minimal interactions" is subjective. The effort for the frontend could be underestimated if more complex client-side logic is required than anticipated.
- **Accessibility Underestimation:** Achieving WCAG 2.1 AA compliance, even the basics, can be time-consuming and may require more effort than the "Low" priority suggests, especially if the team is not experienced in it.
