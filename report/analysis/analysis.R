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

plot(1:1500, estimator, pch=16, axes=FALSE, col=ifelse(estimator > exactValue+CIHalfWidth, "red", ifelse(estimator <
    exactValue-CIHalfWidth, "red", "black")))
axis(1, at=seq(0, 1500, 100), las=1)
axis(2, at=seq(min(estimator), max(estimator), 0.00003))
abline(h=exactValue, col="green", lwd=2)
abline(h=exactValue-CIHalfWidth, col="red", lwd=2)
abline(h=exactValue+CIHalfWidth, col="red", lwd=2)
grid(col="gray")
box()

# ===========================================================================================================
# Generate histogram for the estimator
pdf(paste(plotsDir, "EstimatorHistogram.pdf", sep="/"), width=15, height=8)

hist(estimator, breaks=30, xaxt="n", main="Histogramme des estimateurs ponctuels",
    xlab="Valeur de l'estimateur ponctuel", ylab="Fréquence", col="darkslategray4")
axis(1, at=seq(min(estimator), max(estimator), 0.00003), las=1)
grid(col="gray")
box()

# ===========================================================================================================
# Generate box plot for the estimator
pdf(paste(plotsDir, "BoxPlot.pdf", sep="/"), width=15, height=8)

boxplot(estimator, horizontal=TRUE, main="Boîte à moustache des estimateurs ponctuels", col="darkslategray4",
    xlab="Valeur de l'estimateur ponctuel")

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
abline(h=exactValue, lty=2, col="green")
grid(lwd=2, col="gray")

# legend(
#     x=0.5, # x coordinate of the top left of the legend
#     y=0.5, # y coordinate of the top left of the legend
#     box.lty=0, # line type to surround the legend box (0 for none)
#
#     legend=c("Valeur de l'estimateur", "Valeur exacte de l'espérance"), # sequence of text for the legend
#     pch=c(21, -1), # sequence of point types for the legend; -1 is a nonexistent point
#     pt.bg=c("black", NULL), # sequence of fill colours for the points
#     pt.cex=c(1.8, 1), # symbol size multiplier
#     lty=c(1,3) # sequence of line types for the legend
# )
