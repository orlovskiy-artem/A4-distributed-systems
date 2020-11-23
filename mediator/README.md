### AccountController
POST /students \
GET /students  \
GET /students/{id} \
PUT /students/{id} \
DELETE /students/{id} \
POST /authors \
GET /authors \
GET /authors/{id} \
PUT /authors/{id} \
DELETE /authors/{id}

### CourseProgressController
POST /progress/{courseId}/students/{studentId} \
POST /progress/{courseId}/students/{studentId}/steps/educational-steps/{educationalStepId} \
POST /progress/{courseId}/students/{studentId}/steps/test-steps/{testStepId}

### EducationalMaterialController
GET /courses/{id}
GET /courses/{id}/steps/educational-steps \
GET /courses/{id}/steps/test-steps \
GET /courses/{courseId}/steps/test-steps/{testStepId} \
GET /courses/{courseId}/steps/educational-steps/{educationalStepId} \
PUT /courses/{id} \
PATCH /courses/{id}/status \
POST /courses/{id}/steps/test-steps \
POST /courses/{courseId}/steps/test-steps/{testStepId}/answers \
POST /courses/{id}/steps/educational-steps \
POST /courses/{courseId}/authors/{authorId} \
DELETE /courses/{id} \
DELETE /courses/{courseId}/authors/{authorId} \
DELETE /courses/{courseId}/steps/educational-steps/{educationalStepId} \
DELETE /courses/{courseId}/steps/test-steps/{testStepId} \
DELETE /courses/{courseId}/steps/test-steps/{testStepId}/answers/{testAnswerId} 
