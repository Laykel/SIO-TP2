library(plotrix) # For CI plot

# Store folders
dataDir<-"./"
plotsDir<-"./plots"

# Read observations
observations<-read.csv(file.path(dataDir, "observations.csv"), header=TRUE, sep = ";",
    quote = "\"", dec = ".", fill = TRUE)

# Store values that are often used
estimator<-observations$Average
exactValue<-0.5214054
CIHalfWidth<-0.00005

# Simple summary of our estimators data
options(digits=10)
print(summary(estimator))

## Number of estimators that are out of the CI
print("Number of estimators that are out of the CI:")
print(nrow(subset(observations, Average > exactValue+CIHalfWidth)) + nrow(subset(observations, Average <
    exactValue-CIHalfWidth)))

# ===========================================================================================================
# Generate points plot
pdf(paste(plotsDir, "PointsCloud.pdf", sep="/"), width=15, height=8)

plot(1:1500, estimator, pch=16, axes=FALSE, xlab="Simulation", ylab="Valeur de l'estimateur", col=ifelse(estimator >
    exactValue+CIHalfWidth, "red", ifelse(estimator < exactValue-CIHalfWidth, "red", "black")))
axis(1, at=seq(0, 1500, 100), las=1)
axis(2, at=seq(min(estimator), max(estimator), 0.00003))
abline(h=exactValue, col="green", lwd=2)
abline(h=exactValue-CIHalfWidth, col="red", lwd=2)
abline(h=exactValue+CIHalfWidth, col="red", lwd=2)
grid(col="gray")
box()

# ===========================================================================================================
# Generate histogram for the estimator
pdf(paste(plotsDir, "EstimatorHistogram.pdf", sep="/"), width=12, height=8)

hist(estimator, breaks=30, xaxt="n", main="Histogramme des estimateurs ponctuels",
    xlab="Valeur de l'estimateur ponctuel", ylab="Fréquence", col="darkslategray4")
axis(1, at=seq(min(estimator), max(estimator), 0.000018), las=1)
abline(v=exactValue, col="green", lwd=2)
grid(col="gray")
box()

# ===========================================================================================================
# Generate box plot for the estimator
pdf(paste(plotsDir, "BoxPlot.pdf", sep="/"), width=12, height=8)

boxplot(estimator, horizontal=TRUE, xaxt="n", main="Boîte à moustache des estimateurs ponctuels", col="darkslategray4",
    xlab="Valeur de l'estimateur ponctuel")
axis(1, at=quantile(estimator), las=1)
abline(v=quantile(estimator), lty=2, las=1)
abline(v=exactValue, col="green", lwd=2)

# ===========================================================================================================
# Confidence interval plot
pdf(paste(plotsDir, "CIplot.pdf", sep="/"), width=15, height=8)

x<-1:100
y<-estimator[1:100]
w<-observations$CI95HalfWidth

# Plot Confidence Interval plot
plotCI(x, y, w, pch=16, sfrac=0.003, xlab="Simulation", ylab="Valeur de l'estimateur", col=ifelse(estimator >
    exactValue+CIHalfWidth, "red", ifelse(estimator < exactValue-CIHalfWidth, "red", "black")))
# Add line to show exact value
abline(h=exactValue, lty=2, lwd=2.5, col="green")
grid(lwd=1, col="black")
# legend(x="topleft", c("estimateur", "estimateur dont l'intervalle de confiance ne contient par la valeur exacte",
# "valeur exacte"), cex=.8, col=c("black", "red", "green"), pch=c(16,16,-1), lty=c(1, 1, 3), lwd=c(1,1,2))
