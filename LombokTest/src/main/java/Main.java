import java.util.Date;

public class Main {
    private static String staffFile = "/Users/dmitrijsidelnikov/Library/Mobile Documents/com~apple~CloudDocs/Java/java_basics/07_AdvancedOOPFeatures/LambdaExpressions/data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
////        ArrayList<Employee> staff = loadStaffFromFile();
////        staff.sort(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));
////        System.out.println("Максимальная зарплата среди принятых на работу в 2017г.:");
////        staff.stream().filter(e -> e.getWorkStart().toInstant().atZone(ZoneId.systemDefault()).getYear() == 2017)
////                .map(Employee::getSalary)
////                .max(Integer::compareTo)
////                .ifPresent(System.out::println);
////        for (Employee employee : staff) {
////            System.out.println(employee.toString());
////        }
//        System.out.println(getStringFromFile(staffFile));;


//    }
//
//    private static ArrayList<Employee> loadStaffFromFile() {
//        ArrayList<Employee> staff = new ArrayList<>();
////        try {
////            List<String> lines = Files.readAllLines(Paths.get(staffFile));
////            for (String line : lines) {
////                String[] fragments = line.split("\t");
////                if (fragments.length != 3) {
////                    System.out.println("Wrong line: " + line);
////                    continue;
////                }
////                staff.add(new Employee(
////                        fragments[0],
////                        Integer.parseInt(fragments[1]),
////                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
////                ));
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
//        return staff;
//    }
//
//    public static String getStringFromFile(String path) {
//        StringBuilder builder = new StringBuilder();
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(path));
//            for (String line : lines) {
//                builder.append(line);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return builder.toString();
Employee employee = new Employee("Bob", 100000, new Date());
        System.out.println(employee.getName());

    }
}