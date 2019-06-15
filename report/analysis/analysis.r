dataDir<-"./"

observations<-read.csv(file.path(dataDir, "observations.csv"), header=T, sep = ";",
    quote = "\"", dec = ".", fill = TRUE)

print(observations$NumberOfObs)
