function fn() {
  var env = karate.env; // get java system property 'karate.env'
  var config = {
    baseUrl: "http://localhost:8080/"
  }
  return config;
}