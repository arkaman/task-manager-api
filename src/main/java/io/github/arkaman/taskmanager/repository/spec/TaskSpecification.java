package io.github.arkaman.taskmanager.repository.spec;

import io.github.arkaman.taskmanager.domain.entity.AppUser;
import io.github.arkaman.taskmanager.domain.entity.Task;
import io.github.arkaman.taskmanager.domain.entity.TaskPriority;
import io.github.arkaman.taskmanager.domain.entity.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, cb) ->
                priority == null ? null : cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> titleOrDescriptionContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(cb.coalesce(root.get("description"), "")), pattern)
            );
        };
    }

    public static Specification<Task> hasUser(AppUser user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }
}