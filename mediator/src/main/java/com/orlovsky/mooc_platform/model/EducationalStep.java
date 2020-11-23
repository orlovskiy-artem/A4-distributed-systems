package com.orlovsky.mooc_platform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class EducationalStep implements Step {
    private UUID id;
    @JsonBackReference
    private Course course;
    private URI eduMaterialUri;
    private int position;
    private List<StudentProgressItem> studentProgressItems;

    public UUID getId() {
        return this.id;
    }

    public Course getCourse() {
        return this.course;
    }

    public URI getEduMaterialUri() {
        return this.eduMaterialUri;
    }

    public int getPosition() {
        return this.position;
    }

    public List<StudentProgressItem> getStudentProgressItems() {
        return this.studentProgressItems;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setEduMaterialUri(URI eduMaterialUri) {
        this.eduMaterialUri = eduMaterialUri;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStudentProgressItems(List<StudentProgressItem> studentProgressItems) {
        this.studentProgressItems = studentProgressItems;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EducationalStep)) return false;
        final EducationalStep other = (EducationalStep) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$course = this.getCourse();
        final Object other$course = other.getCourse();
        if (this$course == null ? other$course != null : !this$course.equals(other$course)) return false;
        final Object this$eduMaterialUri = this.getEduMaterialUri();
        final Object other$eduMaterialUri = other.getEduMaterialUri();
        if (this$eduMaterialUri == null ? other$eduMaterialUri != null : !this$eduMaterialUri.equals(other$eduMaterialUri))
            return false;
        if (this.getPosition() != other.getPosition()) return false;
        final Object this$studentProgressItems = this.getStudentProgressItems();
        final Object other$studentProgressItems = other.getStudentProgressItems();
        if (this$studentProgressItems == null ? other$studentProgressItems != null : !this$studentProgressItems.equals(other$studentProgressItems))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EducationalStep;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $course = this.getCourse();
        result = result * PRIME + ($course == null ? 43 : $course.hashCode());
        final Object $eduMaterialUri = this.getEduMaterialUri();
        result = result * PRIME + ($eduMaterialUri == null ? 43 : $eduMaterialUri.hashCode());
        result = result * PRIME + this.getPosition();
        final Object $studentProgressItems = this.getStudentProgressItems();
        result = result * PRIME + ($studentProgressItems == null ? 43 : $studentProgressItems.hashCode());
        return result;
    }

    public String toString() {
        return "EducationalStep(id=" + this.getId() + ", course=" + this.getCourse() + ", eduMaterialUri=" + this.getEduMaterialUri() + ", position=" + this.getPosition() + ", studentProgressItems=" + this.getStudentProgressItems() + ")";
    }
}

