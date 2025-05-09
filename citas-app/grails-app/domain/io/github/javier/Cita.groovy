package io.github.javier
import java.time.LocalDateTime


class Cita {
    Consultorio consultorio
    Doctor doctor
    String nombrePaciente
    LocalDateTime horario


    static constraints = {
        nombrePaciente blank: false
        horario nullable: flase, validtor:{val, obj
            -> if(val.isBefore(LocalDateTime.now())){
                return "No se puede agender de ayer"
            }
        }
    }
}
