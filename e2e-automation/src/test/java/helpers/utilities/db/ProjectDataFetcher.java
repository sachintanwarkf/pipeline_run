package helpers.utilities.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ProjectDataFetcher {

    private Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDataFetcher.class);

    // Constructor to initialize the database connection
    public ProjectDataFetcher(Connection connection) {
        this.connection = connection;
    }

    public Map<Object, Map<String, Object>> fetchAllProjects() throws SQLException {
        Map<Object, Map<String, Object>> projectMap = new LinkedHashMap<>();

        // Step 1: Execute the project query to get project details
        String projectQuery = "SELECT p.source_project_id AS projectId, " +
                "p.project_name AS name, " +
                "asp.assess_product AS projectType, " +
                "p.client_key AS engagementCode " +
                "FROM cohort_project AS p " +
                "LEFT JOIN assess_product AS asp " +
                "ON asp.assess_product_key = p.assess_product_key";

        LOGGER.info("Executing project query to fetch project details");

        try (Statement stmt = connection.createStatement();
             ResultSet projectResultSet = stmt.executeQuery(projectQuery)) {

            // Step 2: For each project, fetch the candidates and build the result
            while (projectResultSet.next()) {
                int projectId = projectResultSet.getInt("projectId");
                String name = projectResultSet.getString("name");
                String projectType = projectResultSet.getString("projectType");
                String engagementCode = projectResultSet.getString("engagementCode");

                LOGGER.info("Fetched project: " + name + ", engagementCode: " + engagementCode);

                // Fetch the candidates for this project
                List<Map<String, Object>> formattedCandidates = getCandidatesForProject(engagementCode);

                // Build the project details
                Map<String, Object> projectData = new LinkedHashMap<>();
                projectData.put("name", name);
                projectData.put("engagementCode", engagementCode);
                projectData.put("endDate", null); // Static value for endDate
                projectData.put("candidates", formattedCandidates);
                projectData.put("createdDate", formatDate(new Date())); // Custom method to format date
                projectData.put("projectType", projectType);
                projectData.put("productType", "select"); // Static value
                projectData.put("potentialAddOn", false); // Static value
                projectData.put("learningAgilityAddOn", false); // Static value
                projectData.put("potentialLevel", "Top Business or Organizational Group Executive"); // Static value
                projectData.put("serviceLevel", "SELF_SERVICE"); // Static value

                // Step 3: Add projectData to the projectMap with engagementCode as the key
                projectMap.put(engagementCode, projectData);
                LOGGER.info("Project data added for engagementCode: " + engagementCode);
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching projects: " + e.getMessage());
            throw e;
        }

        // Step 4: Return the full map of projects
        return projectMap;
    }

    // Helper method to fetch candidates for a given project based on engagementCode
    private List<Map<String, Object>> getCandidatesForProject(String engagementCode) throws SQLException {
        List<Map<String, Object>> candidates = new ArrayList<>();

        String candidateQuery = "SELECT pd.first_name AS firstName, " +
                "pd.last_name AS lastName, " +
                "pca.status AS status, " +
                "pd.client_key " +
                "FROM person_dim AS pd " +
                "INNER JOIN project_candidate_assessment AS pca " +
                "ON pd.person_key = pca.candidate_key " +
                "WHERE pd.client_key::text LIKE '%" + engagementCode + "%'";

        LOGGER.info("Fetching candidates for project with engagementCode: " + engagementCode);

        try (Statement stmt = connection.createStatement();
             ResultSet candidateResultSet = stmt.executeQuery(candidateQuery)) {

            // Build the candidate list in the required format
            while (candidateResultSet.next()) {
                Map<String, Object> candidateData = new LinkedHashMap<>();
                candidateData.put("firstName", candidateResultSet.getString("firstName") != null ? candidateResultSet.getString("firstName") : "Not started");
                candidateData.put("lastName", candidateResultSet.getString("lastName") != null ? candidateResultSet.getString("lastName") : "Candidate");
                candidateData.put("isNonBillable", false); // Static value
                candidateData.put("status", candidateResultSet.getString("status"));

                // Add the candidate to the list
                candidates.add(candidateData);
                LOGGER.info("Candidate added: " + candidateData.get("firstName") + " " + candidateData.get("lastName"));
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching candidates: " + e.getMessage());
            throw e;
        }

        return candidates;
    }

    // Helper method to format the date as 'Month Year'
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        return sdf.format(date);
    }
}
