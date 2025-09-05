# Hospital Management System (Spring Boot)

This is a minimal REST backend for managing **Doctors**, **Patients**, and **Appointments** using Spring Boot 3, JPA, and H2.

## Build & Run

```bash
./mvnw spring-boot:run
```

Then visit Swagger UI: `http://localhost:8080/swagger-ui.html`

### API Summary
- `GET /api/doctors`, `POST /api/doctors`, `GET/PUT/DELETE /api/doctors/{id}`
- `GET /api/patients`, `POST /api/patients`, `GET/PUT/DELETE /api/patients/{id}`
- `GET /api/appointments`, `POST /api/appointments`, `GET/PUT/DELETE /api/appointments/{id}`
- `GET /api/appointments/byDoctor/{doctorId}`
- `GET /api/appointments/byPatient/{patientId}`

### Sample JSON
Create a doctor:
```json
{"name":"Dr. Meera Rao","specialization":"Cardiology","phone":"+91-9999999999","email":"meera@example.com"}
```

Create a patient:
```json
{"name":"Ravi Kumar","dob":"1992-06-12","gender":"M","phone":"+91-8888888888","email":"ravi@example.com"}
```

Create an appointment (1 hour slot):
```json
{
  "doctor":{"id":1},
  "patient":{"id":1},
  "startTime":"2025-09-05T10:00:00",
  "endTime":"2025-09-05T11:00:00",
  "reason":"Consultation",
  "status":"SCHEDULED"
}
```
