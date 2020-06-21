import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final Path PATH_TO_FILE = Paths.get("/Users/dmitrijsidelnikov/Library/Mobile Documents/com~apple~CloudDocs/Java/java_basics/09_FilesAndNetwork/files/movementList.csv");

    public static void main(String[] args) {
        ArrayList<LineOfStatement> bankStatement = loadStatement(PATH_TO_FILE);
        getInfoIncomAmount(bankStatement);
        getInfoExpenditureAmount(bankStatement);
        getInfoExpenditures(bankStatement);
    }

    private static ArrayList<LineOfStatement> loadStatement(Path pathToFile) {
        ArrayList<LineOfStatement> bankStatement = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(pathToFile);

            for (String line : lines.subList(1, lines.size())) {
                String[] fragments = line.split(",", 8);
                if (fragments[7].contains("\"")) {
                    fragments[7] = fragments[7].replace(',', '.').replace("\"", "");
                }
                if (fragments.length < 8 || !fragments[7].matches("(\\d+\\.*\\d*)")) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                String[] v = fragments[5].replaceAll("([/\\\\])", " ").split("\\s{4,}");

                bankStatement.add(new LineOfStatement(fragments[6], fragments[7], v[1].substring(v[1].indexOf(' '))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bankStatement;
    }

    private static void getInfoIncomAmount(ArrayList<LineOfStatement> bankStatement) {
        System.out.print("Сумма доходов:" + "\n" + "\t");
        bankStatement.stream()
                .map(LineOfStatement::getIncome)
                .reduce(Double::sum)
                .ifPresent(System.out::print);
        System.out.println("руб.");
    }

    private static void getInfoExpenditureAmount(ArrayList<LineOfStatement> bankStatement) {
        System.out.print("Сумма расходов:" + "\n" + "\t");
        bankStatement.stream()
                .map(LineOfStatement::getExpenditure)
                .reduce(Double::sum)
                .ifPresent(System.out::print);
        System.out.println("руб.");
    }

    private static void getInfoExpenditures(ArrayList<LineOfStatement> bankStatement) {
        System.out.println("\n" + "Суммы расходов по организациям:");
        LinkedHashMap<String, Double> result = bankStatement
                .stream()
                .filter(s -> s.getExpenditure() > 0)
                .collect(Collectors
                        .collectingAndThen(Collectors
                                        .toMap(s -> s.getVendor().trim(), LineOfStatement::getExpenditure, Double::sum),
                                map -> map.entrySet().stream()))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, n) -> old, LinkedHashMap::new));

        result.forEach((key, value) -> System.out.printf("%-35s %10.2f руб.\n", key, value));
    }
}
