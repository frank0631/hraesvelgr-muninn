package com.frank0631.muninn;

import com.frank0631.nidhogg.echo.TEchoService;
import com.frank0631.nidhogg.calculator.TCalculatorService;
import com.frank0631.nidhogg.calculator.TOperation;

import org.apache.thrift.*;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;

public class ThriftHelper {

	private static String thriftEndpointEcho = "/thrift/echo/echo/";
	private static String thriftEndpointCalculator = "/thrift/calculator/";
	private String APIAddress;

	public ThriftHelper(String APIAddress) {
		this.APIAddress = APIAddress;
	}

	public String echo(String msg) {
		try {
			TTransport transport = new THttpClient(APIAddress
					+ thriftEndpointEcho);
			TProtocol protocol = new TJSONProtocol(transport);
			TEchoService.Client client = new TEchoService.Client(protocol);

			transport.open();
			String echo = client.echo(msg);
			transport.close();

			return echo;

		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int calc(int num1, int num2, TOperation op) {
		try {
			TTransport transport = new THttpClient(APIAddress
					+ thriftEndpointCalculator);
			TProtocol protocol = new TJSONProtocol(transport);
			TCalculatorService.Client client = new TCalculatorService.Client(
					protocol);
			transport.open();

			int result = client.calculate(num1, num2, op);
			transport.close();

			return result;

		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}

		return -1;
	}
}
