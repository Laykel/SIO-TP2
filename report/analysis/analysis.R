library(plotrix)

dataDir<-"./"

# Read observations
observations<-read.csv(file.path(dataDir, "observations.csv"), header=TRUE, sep = ";",
    quote = "\"", dec = ".", fill = TRUE)

estimator<-observations$Average

# Generate histogram for the estimator
hist(estimator, breaks=30, xaxt="n", main="Histogramme des estimateurs ponctuels", xlab="Valeur de
l'estimateur
ponctuel", ylab="Fréquence", col="darkslategray4",
    panel.first={
        axis(1, at=seq(min(estimator), max(estimator), 3))
        box()
        grid()
    }
)

# Generate box plot for the estimator
boxplot(estimator, horizontal=TRUE, main="Boîte à moustache des estimateurs ponctuels", col="darkslategray4")

x<-1:100
y<-estimator[1:100]
w<-observations$CI95HalfWidth

pdf("RplotCI.pdf", width=15, height=8)

# Plot Confidence Interval plot
plotCI(x, y, w, pch=16, sfrac=0.003, xlab="Simulation", ylab="Valeur de l'estimateur")
# Add line to show exact value
abline(h=0.5214054, lty=2, col="green")
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
