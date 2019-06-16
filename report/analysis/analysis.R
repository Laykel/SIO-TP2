dataDir<-"./"

observations<-read.csv(file.path(dataDir, "observations.csv"), header=TRUE, sep = ";",
    quote = "\"", dec = ".", fill = TRUE)

print(observations$NumberOfObs)

hist(observations$Average, main="Histogramme des estimateurs ponctuels", xlab="Estimateur ponctuel", ylab="Fréquence",
col="darkslategray4")

boxplot(observations$Average, horizontal=TRUE, main="Boîte à moustache des estimateurs ponctuels", col="darkslategray4")