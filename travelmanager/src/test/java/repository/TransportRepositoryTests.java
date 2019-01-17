package repository;

import static com.transport.travelmanager.utils.CreatEntityRandomDataHelper.getNewTransport;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.repositories.TransportRepository;
import com.transport.travelmanager.repositories.rowmappers.TransportRowMapper;;

@RunWith(MockitoJUnitRunner.class)
public class TransportRepositoryTests {
	@InjectMocks
	private TransportRepository transportRepository;
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testFindById() {
		Transport transport = getNewTransport();
		
		when(jdbcTemplate.queryForObject(anyString(), any(TransportRowMapper.class))).thenReturn(transport);
		
		Transport tTest = transportRepository.findById(transport.getId());
		
		assertEquals("The Transport received was different than the expected",transport,tTest);
	}
	@Test
	public void testCreteNewTrasnport() {
		Transport transport = getNewTransport();
		
		when(jdbcTemplate.update(anyString(), any(new Object[] {}.getClass()),any(new int[] {}.getClass()))).thenReturn(1);
		when(jdbcTemplate.queryForObject(anyString(), any(TransportRowMapper.class))).thenReturn(transport);
		
		Transport tTest = transportRepository.createNewTransport(transport.getDestiny().getId(),transport.getVehicle().getId(),transport.getVehicle().getCapacity(),transport.getDateTimeTravelStart(),new String());
		
		assertEquals("The Transport received was different than the expected",transport,tTest);
	}

}
