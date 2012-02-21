package thredds.server.opendap;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import thredds.mock.web.MockTdsContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/WEB-INF/applicationContext-tdsConfig.xml"},loader=MockTdsContextLoader.class)
public class OpendapServletTest {

	@Autowired
	private MockServletConfig servletConfig;
	
	private OpendapServlet opendapServlet;
	
	
	@Before
	public void setUp() throws Exception {
		
		opendapServlet =new OpendapServlet();
		opendapServlet.init(servletConfig);
		opendapServlet.init();
		
	}
	
	@Test
	public void doGetTest() throws UnsupportedEncodingException{
		
		String mockURI = "/thredds/dodsC/test/testData.nc.ascii";
		String mockQueryString ="valtime[0:1:0]";
		MockHttpServletRequest request = new MockHttpServletRequest("GET", mockURI);		
		request.setContextPath("/thredds");
		request.setQueryString(mockQueryString);
		request.setPathInfo("/test/testData.nc.ascii"); // ?
		MockHttpServletResponse response = new MockHttpServletResponse();

		opendapServlet.doGet(request, response);
		
		String strResponse = response.getContentAsString();
		
		assertEquals("Dataset {\n    Float64 valtime[record = 1];\n} test/testData.nc;\n---------------------------------------------\nvaltime[1]\n102840.0\n\n",
				strResponse);
		
	}
	
}