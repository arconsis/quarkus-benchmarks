package com.arconsis.benchmarks.quarkus.endpoints

import com.arconsis.benchmarks.quarkus.dto.ClearResult
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/managing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ManagingResource(private val personService: SyncPersonService) {

	@DELETE
	@Path("/clear")
	fun clear(): ClearResult {
		val deletedPersons = personService.deleteAllPersons()
		return ClearResult(deletedPersons)
	}
}
