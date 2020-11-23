package com.orlovsky.mooc_platform.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CourseAction {
    private final UUID courseId;
    private final UUID studentId;
    private final ActionType actionType;
    private final ZonedDateTime actionTime;
    private final Integer score;

    public CourseAction(UUID courseId,
                        UUID studentId,
                        ActionType actionType,
                        ZonedDateTime actionTime,
                        Integer score) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.actionType = actionType;
        this.actionTime = actionTime;
        this.score = score;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public Integer getScore() {
        return score;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public ZonedDateTime getActionTime() {
        return actionTime;
    }
}
