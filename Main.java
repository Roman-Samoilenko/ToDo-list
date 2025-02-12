import java.util.Scanner;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while(true) {
            System.out.println("Введите одну из команд: add, add first, look, max, del id/time/shortest, range, completed, free time, exit.");

            String in = sc.nextLine();

            switch (in) {
                case "add":
                    System.out.println("Введите время начала задачи c 09:00 до 21:00 (формат чч:мм):");
                    String start = sc.nextLine();
                    LocalTime startTime;
                    try {
                         startTime = LocalTime.parse(start);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println("Введите продолжительность задачи в минутах:");
                    int duration;
                    try {
                        duration = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат ввода");
                        break;
                    }
                    System.out.println("Введите название задачи:");
                    String name = sc.nextLine();
                    manager.addTask(startTime, duration, name);
                    break;

                case "look":
                    manager.printAllTasks();
                    break;

                case "max":
                    try {
                        System.out.println(manager.getLongestTask());
                    } catch (Exception e) {
                        System.out.println("Cписок задач пуст.");
                    }
                    break;

                case "del id":
                    System.out.println("Введите номер(id) задачи для удаления:");
                    int id;
                    try {
                        id = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат ввода");
                        break;
                    }
                    manager.removeTask(id);
                    break;

                case "range":
                    System.out.println("Введите время начала промежутка (формат чч:мм):");
                    LocalTime startRange;
                    String startR = sc.nextLine();
                    try {
                        startRange = LocalTime.parse(startR);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println("Введите время конца промежутка (формат чч:мм):");
                    LocalTime endRange;
                    String endR = sc.nextLine();
                    try {
                        endRange = LocalTime.parse(endR);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println(manager.getTasksInRange(startRange, endRange));
                    break;

                case "completed":
                    System.out.println("Введите время (формат чч:мм):");
                    LocalTime completedTasksBefore;
                    String timeS = sc.nextLine();
                    try {
                        completedTasksBefore = LocalTime.parse(timeS);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println("В " + completedTasksBefore + " эти задачи будут выполнены:");
                    System.out.println(manager.getCompletedTasksBefore(completedTasksBefore));
                    break;

                case "del time":
                    System.out.println("Введите время начала промежутка, в котором если задача начинается, она будет удалена (формат чч:мм):");
                    LocalTime startRangeDel;
                    String startRD = sc.nextLine();
                    try {
                        startRangeDel = LocalTime.parse(startRD);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println("Введите время конца промежутка, в котором если задача начинается, она будет удалена (формат чч:мм):");
                    LocalTime endRangeDel;
                    String endRD = sc.nextLine();
                    try {
                        endRangeDel = LocalTime.parse(endRD);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    manager.removeTasksInStartRange(startRangeDel, endRangeDel);
                    System.out.println("Все задачи, начинающиеся на этом временном промежутке удалены.");
                    break;

                case "free time":
                    System.out.println("Введите время начала промежутка, который хотите освободить (формат чч:мм):");
                    LocalTime startRangeFree;
                    String startRF = sc.nextLine();
                    try {
                        startRangeFree = LocalTime.parse(startRF);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    System.out.println("Введите время конца промежутка, который хотите освободить (формат чч:мм):");
                    LocalTime endRangeFree;
                    String endRF = sc.nextLine();
                    try {
                        endRangeFree = LocalTime.parse(endRF);
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат времени.");
                        break;
                    }
                    manager.removeAndTrimTasksInRange(startRangeFree, endRangeFree);
                    System.out.println("Указанное время освобождено.");
                    break;

                case "del shortest":
                    manager.removeShortestTasks();
                    break;

                case "add first":
                    System.out.println("Введите продолжительность задачи в минутах:");
                    int durationF;
                    try {
                        durationF = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Ошибка: неверный формат ввода");
                        break;
                    }
                    System.out.println("Введите название задачи:");
                    String nameF = sc.nextLine();
                    manager.addTaskInFirst(durationF, nameF);
                    break;

                case "exit":
                    return;

            }
        }
    }
}
