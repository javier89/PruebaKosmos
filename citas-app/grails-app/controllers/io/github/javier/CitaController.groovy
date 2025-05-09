package io.github.javier

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CitaController {

    CitaService citaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond citaService.list(params), model:[citaCount: citaService.count()]
    }

    def show(Long id) {
        respond citaService.get(id)
    }

    def create() {
        respond new Cita(params)
    }

    def save(Cita cita) {
        if (cita == null) {
            notFound()
            return
        }

        try {
            citaService.save(cita)
        } catch (ValidationException e) {
            respond cita.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cita.label', default: 'Cita'), cita.id])
                redirect cita
            }
            '*' { respond cita, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond citaService.get(id)
    }

    def update(Cita cita) {
        if (cita == null) {
            notFound()
            return
        }

        try {
            citaService.save(cita)
        } catch (ValidationException e) {
            respond cita.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cita.label', default: 'Cita'), cita.id])
                redirect cita
            }
            '*'{ respond cita, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        citaService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cita.label', default: 'Cita'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cita.label', default: 'Cita'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
