import java.util.HashMap;
import java.util.Map;

public class ContentModel {
    private Map<String, String> courseContentMap = new HashMap<>();
    private Map<String, Map<String, Double>> performanceMap = new HashMap<>();
    private String selectedCourse;

    public String getContent(String course) {
        return courseContentMap.getOrDefault(course, "");
    }

    public void setContent(String course, String content) {
        courseContentMap.put(course, content);
    }

    public Map<String, String> getAllContent() {
        return courseContentMap;
    }

    public void addPerformance(String course, String studentId, double score) {
        performanceMap.putIfAbsent(course, new HashMap<>());
        performanceMap.get(course).put(studentId, score);
    }

    public Map<String, Map<String, Double>> getAllPerformance() {
        return performanceMap;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public void addCourse(String course) {
        if (!courseContentMap.containsKey(course)) {
            courseContentMap.put(course, "");
        }
    }
}