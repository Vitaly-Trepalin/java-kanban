import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> browsingHistory = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        if (browsingHistory.containsKey(task.getId())) {
            removeNode(browsingHistory.get(task.getId()));
        }
        linkLast(task);
        browsingHistory.put(task.getId(), tail);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(browsingHistory.get(id));
        browsingHistory.remove(id);
    }

    public void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public List<Task> getTasks() {
        List<Task> allTasks = new ArrayList<>();
        if (head != null) {
            Node<Task> currentNode = head;
            while (currentNode.next != null) {
                allTasks.add(currentNode.data);
                currentNode = currentNode.next;
            }
            allTasks.add(tail.data);
        }
        return allTasks;
    }

    public void removeNode(Node<Task> node) {
        if (head != null) {
            if (head.equals(node)) {
                head = head.next;
//                head.prev = null;
                return;
            }
            Node<Task> currentNode = head;
            while (currentNode.next != null) {
                if (currentNode.next.equals(node)) {
                    if (currentNode.next.next == null) {
                        tail = currentNode;
                        tail.next = null;
                        return;
                    } else {
                        currentNode.next = currentNode.next.next;
                        currentNode.next.prev = currentNode;
                        return;
                    }

                }
                currentNode = currentNode.next;
            }
            if (tail.equals(node)) {
                tail = tail.prev;
                tail.next = null;
            }
        }
    }

    private static class Node<T> {
        private final T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }
}


