package main.controller;

public interface RequestHandler {
	public Response run(Object object) throws InvalidRequestException;
}