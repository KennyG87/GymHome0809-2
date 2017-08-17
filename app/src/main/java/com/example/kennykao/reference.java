

    private class RegisterTask1 extends AsyncTask<String, Integer, String> {

        private static final String TAG = "RegisterActivity";

        @Override
        protected String doInBackground(String... params) {
            String url =  params[0];
            String outStr="";
            String jsonIn;
//            Gson gson = new Gson();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            outStr = gson.toJson(member);

            try {
                jsonIn = getRemoteData(url, outStr);
                Log.d(TAG, "jsonin=" + jsonIn);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                return null;
            }
            return null;
        }
        private String getRemoteData(String url, String jsonOut) throws IOException {
            StringBuilder jsonIn = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                bw.write(jsonOut);
                Log.d(TAG, "jsonOut: " + jsonOut);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonIn.append(line);
                }
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
            connection.disconnect();
            Log.d(TAG, "jsonInqq: " + jsonIn);
            return jsonIn.toString();
        }
    }



}

