package com.annikadietz.shoppy_shoppingbuddy

import org.json.JSONObject

class MockGoogleDirectionsService: GoogleDirectionsAPI {
    var counter = 0
    override fun getDirections(url: String): JSONObject {
        if(counter==0){
            return JSONObject("{\n" +
                    "   \"routes\" : [\n" +
                    "      {\n" +
                    "         \"legs\" : [\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"2 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"2 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"1 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            }\n" +
                    "         ]\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"status\" : \"OK\"\n" +
                    "}")
        }
        if(counter==2){
            return JSONObject("{\n" +
                    "   \"routes\" : [\n" +
                    "      {\n" +
                    "         \"legs\" : [\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"2 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"2 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"1 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            }\n" +
                    "         ]\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"status\" : \"OK\"\n" +
                    "}")
        }
        if(counter==3){
            return JSONObject("{\n" +
                    "   \"routes\" : [\n" +
                    "      {\n" +
                    "         \"legs\" : [\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"2 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"2 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"1 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            }\n" +
                    "         ]\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"status\" : \"OK\"\n" +
                    "}")
        }
        if(counter==4){
            return JSONObject("{\n" +
                    "   \"routes\" : [\n" +
                    "      {\n" +
                    "         \"legs\" : [\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"2 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"0.5 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 50\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"2 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"1 km\",\n" +
                    "                  \"value\" : 202\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"1 min\",\n" +
                    "                  \"value\" : 46\n" +
                    "               }\n" +
                    "            }\n" +
                    "         ]\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"status\" : \"OK\"\n" +
                    "}")
        }
        counter += 1

        return JSONObject()
    }
}