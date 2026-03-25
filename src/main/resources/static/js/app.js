document.addEventListener('DOMContentLoaded', () => {
    const taskForm = document.getElementById('task-form');
    const taskList = document.getElementById('task-list');
    const searchInput = document.getElementById('search');
    const priorityFilter = document.getElementById('filter-priority');
    const tagFilter = document.getElementById('filter-tag');
    const sortBy = document.getElementById('sort-by');
    const prevPageBtn = document.getElementById('prev-page');
    const nextPageBtn = document.getElementById('next-page');
    const pageInfo = document.getElementById('page-info');
    const editModal = document.getElementById('edit-modal');
    const editTaskForm = document.getElementById('edit-task-form');
    const closeModalBtn = document.querySelector('.close-btn');

    let currentPage = 0;
    let totalPages = 1;

    const fetchAndRenderTasks = async () => {
        const search = searchInput.value;
        const priority = priorityFilter.value;
        const tag = tagFilter.value;
        const sort = sortBy.value;

        let url = `/api/tasks?page=${currentPage}&sort=${sort}`;
        if (search) url += `&search=${search}`;
        if (priority) url += `&priority=${priority}`;
        if (tag) url += `&tag=${tag}`;

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Failed to fetch tasks');
            }
            const page = await response.json();
            renderTasks(page.content);
            updatePagination(page);
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    };

    const renderTasks = (tasks) => {
        taskList.innerHTML = '';
        tasks.forEach(task => {
            const li = document.createElement('li');
            li.className = task.completed ? 'completed' : '';
            li.innerHTML = `
                <div>
                    <strong class="task-title">${task.title}</strong>
                    <p>${task.description || ''}</p>
                    <small>Priority: ${task.priority} | Due: ${task.dueDate || 'N/A'}</small>
                    <div class="tags">${task.tags.map(tag => `<span>${tag}</span>`).join('')}</div>
                </div>
                <div class="task-actions">
                    <button class="edit-btn" data-id="${task.id}">Edit</button>
                    <button class="complete-btn" data-id="${task.id}">${task.completed ? 'Undo' : 'Complete'}</button>
                    <button class="delete-btn" data-id="${task.id}">Delete</button>
                </div>
            `;
            taskList.appendChild(li);
        });
    };

    const updatePagination = (page) => {
        currentPage = page.number;
        totalPages = page.totalPages;
        pageInfo.textContent = `Page ${currentPage + 1} of ${totalPages}`;
        prevPageBtn.disabled = page.first;
        nextPageBtn.disabled = page.last;
    };

    taskForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const priority = document.getElementById('priority').value;
        const dueDate = document.getElementById('due-date').value;
        const tags = document.getElementById('tags').value.split(',').map(tag => tag.trim());

        const taskData = { title, description, priority, dueDate, tags };

        try {
            const response = await fetch('/api/tasks', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(taskData)
            });

            if (response.ok) {
                taskForm.reset();
                fetchAndRenderTasks();
            } else {
                console.error('Failed to create task');
            }
        } catch (error) {
            console.error('Error creating task:', error);
        }
    });

    taskList.addEventListener('click', async (e) => {
        const id = e.target.dataset.id;
        if (e.target.classList.contains('delete-btn')) {
            try {
                const response = await fetch(`/api/tasks/${id}`, { method: 'DELETE' });
                if (response.ok) {
                    fetchAndRenderTasks();
                } else {
                    console.error('Failed to delete task');
                }
            } catch (error) {
                console.error('Error deleting task:', error);
            }
        }

        if (e.target.classList.contains('edit-btn')) {
            const id = e.target.dataset.id;
            openEditModal(id);
        }

        if (e.target.classList.contains('complete-btn')) {
            const taskElement = e.target.closest('li');
            const isCompleted = taskElement.classList.contains('completed');
            try {
                const response = await fetch(`/api/tasks/${id}`, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ completed: !isCompleted })
                });
                if (response.ok) {
                    fetchAndRenderTasks();
                } else {
                    console.error('Failed to update task');
                }
            } catch (error) {
                console.error('Error updating task:', error);
            }
        }
    });

    [searchInput, priorityFilter, tagFilter, sortBy].forEach(el => {
        el.addEventListener('change', () => {
            currentPage = 0;
            fetchAndRenderTasks();
        });
    });

    prevPageBtn.addEventListener('click', () => {
        if (currentPage > 0) {
            currentPage--;
            fetchAndRenderTasks();
        }
    });

    nextPageBtn.addEventListener('click', () => {
        if (currentPage < totalPages - 1) {
            currentPage++;
            fetchAndRenderTasks();
        }
    });

    const openEditModal = async (id) => {
        try {
            const response = await fetch(`/api/tasks/${id}`);
            if (!response.ok) {
                throw new Error('Failed to fetch task details');
            }
            const task = await response.json();
            document.getElementById('edit-task-id').value = task.id;
            document.getElementById('edit-title').value = task.title;
            document.getElementById('edit-description').value = task.description;
            document.getElementById('edit-priority').value = task.priority;
            document.getElementById('edit-due-date').value = task.dueDate;
            document.getElementById('edit-tags').value = task.tags.join(', ');
            editModal.style.display = 'block';
        } catch (error) {
            console.error('Error opening edit modal:', error);
        }
    };

    const closeEditModal = () => {
        editModal.style.display = 'none';
    };

    closeModalBtn.addEventListener('click', closeEditModal);
    window.addEventListener('click', (e) => {
        if (e.target == editModal) {
            closeEditModal();
        }
    });

    editTaskForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = document.getElementById('edit-task-id').value;
        const taskData = {
            title: document.getElementById('edit-title').value,
            description: document.getElementById('edit-description').value,
            priority: document.getElementById('edit-priority').value,
            dueDate: document.getElementById('edit-due-date').value,
            tags: document.getElementById('edit-tags').value.split(',').map(tag => tag.trim()),
        };

        try {
            const response = await fetch(`/api/tasks/${id}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(taskData)
            });

            if (response.ok) {
                closeEditModal();
                fetchAndRenderTasks();
            } else {
                console.error('Failed to update task');
            }
        } catch (error) {
            console.error('Error updating task:', error);
        }
    });

    fetchAndRenderTasks();
});
