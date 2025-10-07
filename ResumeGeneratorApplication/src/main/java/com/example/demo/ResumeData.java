package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ResumeData {
    // Personal Information
    private String name = "";
    private String email = "";
    private String phone = "";
    private String address = "";
    private String linkedin = "";
    private String website = "";
    private String photo; // Added field for the photo

    // Professional Summary
    private String summary = "";

    // Work Experience
    private List<Experience> experiences = new ArrayList<>();

    // Education
    private List<Education> educations = new ArrayList<>();

    // Skills
    private List<String> skills = new ArrayList<>();

    public ResumeData() {
        // Initialize with some empty objects for the form
        this.experiences.add(new Experience());
        this.educations.add(new Education());
        this.skills.add("");
    }

    // Getters and Setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    // --- Getter and Setter for the new photo field ---
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    // -------------------------------------------------

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }


    // Inner classes for Experience and Education
    public static class Experience {
        private String jobTitle = "";
        private String company = "";
        private String duration = "";
        private String description = "";

        public String getJobTitle() { return jobTitle; }
        public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class Education {
        private String degree = "";
        private String institution = "";
        private String gradYear = "";

        public String getDegree() { return degree; }
        public void setDegree(String degree) { this.degree = degree; }
        public String getInstitution() { return institution; }
        public void setInstitution(String institution) { this.institution = institution; }
        public String getGradYear() { return gradYear; }
        public void setGradYear(String gradYear) { this.gradYear = gradYear; }
    }
}

