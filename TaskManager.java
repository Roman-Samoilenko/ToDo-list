import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskManager {

    private final static ArrayList<Task> tasks = new ArrayList<>();

    private boolean isValidTask(LocalTime startTime, int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        if (startTime.isBefore(LocalTime.of(9, 0)) || endTime.isAfter(LocalTime.of(21, 0)) ) {
            return false;
        }
        for (Task task : tasks) {
            if ((startTime.isBefore(task.getEndTime()) && endTime.isAfter(task.getStartTime()))) {
                return false;
            }
        }
        return true;
    }

    public void addTask(LocalTime startTime, int duration, String name) {
        if (!isValidTask(startTime, duration)) {
            System.out.println("Некорректное время или пересечение с существующими задачами, задача не создана.");
            return;
        }
        tasks.add(new Task(startTime, duration, name));
        System.out.println("Задача создана.");
    }

    public void removeTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public void printAllTasks() {
        if(tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }
        tasks.sort(Comparator.comparing(Task::getStartTime));
        for (Task t : tasks) {
            System.out.println(t);
        }

    }

    public Task getLongestTask() {
        tasks.sort(Comparator.comparing(Task::getDuration).thenComparing(Comparator.comparing(Task::getStartTime).reversed()));
        return tasks.getLast();
    }

    public ArrayList<Task> getTasksInRange(LocalTime startRange, LocalTime endRange) {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task task : tasks) {
            if ((task.getStartTime().isBefore(endRange) && task.getEndTime().isAfter(startRange))) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public ArrayList<Task> getCompletedTasksBefore(LocalTime time) {
        ArrayList<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getEndTime().isBefore(time)) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public void removeTasksInStartRange(LocalTime startRange, LocalTime endRange) {
        tasks.removeIf(task ->
                task.getStartTime().isAfter(startRange) && task.getStartTime().isBefore(endRange));
    }

    public void removeAndTrimTasksInRange(LocalTime startRange, LocalTime endRange) {
        ArrayList<Task> tasksToRemove = new ArrayList<>();

        for (Task task : tasks) {
            LocalTime taskStart = task.getStartTime();
            LocalTime taskEnd = task.getEndTime();
            // Проверка на пересечение задачи и введённого промежутка
            if (taskStart.isBefore(endRange) && taskEnd.isAfter(startRange)) {
                // Если задача полностью находится в диапазоне или полностью покрывает диапазон, удаляем её
                if (    (taskStart.isAfter(startRange) && taskEnd.isBefore(endRange)) ||
                        (taskStart.isBefore(startRange) && taskEnd.isAfter(endRange))) {
                    tasksToRemove.add(task);
                } else { // Если задача частично перекрывает диапазон, обрезаем её
                    if (taskStart.isBefore(startRange)) {
                        task.setEndTime(startRange);
                    }
                    if (taskEnd.isAfter(endRange)) {
                        task.setStartTime(endRange);
                    }
                }
            }
        }
        tasks.removeAll(tasksToRemove);
    }

    public void removeShortestTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            return;
        }

        int mn = Integer.MAX_VALUE;
        for(Task task: tasks){
            if(task.getDuration() < mn)
                mn = task.getDuration();
        }

        int finalMn = mn;
        tasks.removeIf(task -> task.getDuration() == finalMn);

        System.out.println("Все задачи с минимальной продолжительностью ("+ finalMn +" мин.) удалены.");
    }

    public void addTaskInFirst(int duration, String name){

        LocalTime cnt = LocalTime.of(9, 0);
        while(cnt.plusMinutes(duration).isBefore(LocalTime.of(21, 0))){
            if(isValidTask(cnt, duration)){
                System.out.println("Нашёлся первый свободный временной промежуток: " + cnt + " - " + cnt.plusMinutes(duration));
                addTask(cnt, duration, name);
                return;
            }
            cnt = cnt.plusMinutes(1);
        }
        System.out.println("Не удалось добавить задачу, не нашёлся свободный временной промежуток нужной длины.");

    }
}
