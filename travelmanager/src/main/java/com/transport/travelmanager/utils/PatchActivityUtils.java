package com.transport.travelmanager.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.transport.travelmanager.exceptions.TravelManagerException;

public class PatchActivityUtils {
	public static <T> T patchActivityEntity(JsonNode patchSingleOperation, String entityJsonString, Class<T> classType) throws IOException, TravelManagerException {
		entityJsonString = patchActivityString(patchSingleOperation, entityJsonString);
		return JackJsonUtils.jsonStringToObject(entityJsonString, classType);
	}
	public static <T> T patchActivityEntity(JsonNode patchSingleOperation, T entity, Class<T> classType) throws IOException, TravelManagerException {
		String entityJsonString = JackJsonUtils.entityToJsonString(entity);
		entityJsonString = patchActivityString(patchSingleOperation, entityJsonString);
		return  JackJsonUtils.jsonStringToObject(entityJsonString, classType);
	}
	public static String patchActivityString (JsonNode patchSingleOperation, String entity) throws TravelManagerException {
		String patchOperation = patchSingleOperation.get("op").asText().toLowerCase() ;
		String path = patchSingleOperation.get("path").asText();
		JsonPatchBuilder jsonPatchBuilder = Json.createPatchBuilder();
		JsonPatch jPatch;
		switch (patchOperation) {
		case "add":
			String aaddValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.add(path, aaddValue).build();
			break;
		case "remove":
			jPatch = jsonPatchBuilder.remove(path).build();
			break;
		case "replace":
			String replaceValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.replace(path, replaceValue).build();
			break;	
		case "move":
			String from = patchSingleOperation.get("from").asText();
			jPatch = jsonPatchBuilder.move(from,path).build();
			break;				
		case "test":
			String testValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.test(path, testValue).build();
			break;	
		default:
			throw new TravelManagerException("The Patch Operation "+patchOperation+" isn't valid");
		}
		InputStream  personInputStream = new ByteArrayInputStream(entity.getBytes());
        JsonReader reader = Json.createReader(personInputStream);
        JsonStructure jsonStructure1 = reader.read();
        JsonStructure jsonStructure2 = jPatch.apply(jsonStructure1);
        reader.close();
        return jsonStructure2.toString();
	}
}