/**
 * TransactionHistogram
 *
 * Object encapsulating the histogram interactions for all transactions. This
 * visualization will show a histogram of the contribution sizes for the list
 * of contributions that the render method is given.
 *
 * @constructor
 * @params {d3.selection} selector
 * @params {d3.dispatch} dispatch
 */
var TransactionHistogram = function (selector, dispatch) {
    this.dispatch = dispatch;
    this.selector = selector;

    // Viz parameters
    this.height = 450;
    this.width = 330;
    var marginBottom = 20;

    // Create histogram svg container
    this.svg = this.selector.append('svg')
        .attr('width', this.width)
        .attr('height', this.height + marginBottom)  // space needed for ticks
        .append('g');

    // Bin counts
    this.bins = [50, 200, 500, 1000, 10000, 50000, 100000, 1000000];
    this.histogramLayout = d3.layout.histogram()
      .bins([0].concat(this.bins))
      .value(function(d) {return d.amount;});

    // Initialize default color
    this.setHistogramColor(this.colorStates.DEFAULT);
};

/**
 * render(data)
 *
 * Given a data list, histogram the contribution amounts. The visualization will
 * show how many contributions were made between a set of monetary ranges defined
 * by the buckets `this.bins`.
 *
 * The data is expected in the following format:
 *     data = [
 *        {'state': 'CA', 'amount': 200},
 *        {'state': 'CA', 'amount': 10},
 *        ...
 *     ]
 *
 * @params {Array} data is an array of objects with keys 'state' and 'amount'
 */
TransactionHistogram.prototype.render = function(data) {
    // Needed to access the component in anonymous functions
    var that = this;

    // Generate the histogram data with D3
    var histogramData = this.hasScaleSet() ? this.histogramLayout(data) : this.setScale(data);

    /** Histogram visualization setup **/
    // Select the bar groupings and bind to the histogram data
    var bar = this.svg.selectAll('.bar')
          .data(histogramData, function(d) {return d.x;});

    /* Enter phase */
    // Add a new grouping
    var entered = bar
        .enter()
        .append('g')
        .attr('class', 'bar')
        .attr('transform', function(d) {
            return 'translate(' + that.xScale(d['x']) + ', ' + (that.height - that.yScale(d.length)) + ')'
        });
    // Add a rectangle to this bar grouping
    entered.append('rect')
        .attr('fill', that.currentColorState)
        .attr('x', 1)
        .attr('width', function (d) {
            return that.widthOfBar(d) - 1
        })
        .attr('height', function(d) {
            return that.yScale(d.length)
        });
    // Add text to this bar grouping
    entered.append('text')
        .attr('dy', '0.75em')
        .attr('y', function (d) {
            return that.isBarLargeEnough(d) ? '1.5em': '-1.5em';
        })
        .attr('fill', function (d) {
            return that.isBarLargeEnough(d) ? '#f9f9f9' :'#a9a9a9';
        })
        .attr('x', function (d) {
            return that.widthOfBar(d) / 2
        })
        .attr("text-anchor", "middle")
        .text(function(d) { return that.formatBinCount(d.length)});

    /** Update phase */
    bar.transition().duration(1000)
        .attr('transform', function(d) {
            return 'translate(' + that.xScale(d['x']) + ', ' + (that.height - that.yScale(d.length)) + ')'
        });

    bar.select('rect').transition().duration(1000)
        .attr('fill', that.currentColorState)
        .attr('height', function(d) {
            return that.yScale(d.length)
        });

    bar.select('text').transition().duration(1000)
        .attr('y', function (d) {
            return that.isBarLargeEnough(d) ? '1.5em': '-1.5em';
        })
        .attr('fill', function (d) {
            return that.isBarLargeEnough(d) ? '#f9f9f9' :'#a9a9a9';
        })
        .text(function(d) { return that.formatBinCount(d.length)});

    /** Exit phase */
    bar.exit().remove();

    // Draw / update the axis as well
    this.drawAxis();
};


/**
 * formatBinCount(number)
 *
 * Helper function for formatting the bin count.
 * @params {number} number
 * @return number
 */
TransactionHistogram.prototype.formatBinCount = function (number) {
    if (number < 100)
      return number;
    return d3.format('.3s')(number);
};

/*
 * Helper function for determing the position of text.
 *
 * @params {Object} a single transaction/state object
 * @return {boolean} true if the text should go below the bar
 */
TransactionHistogram.prototype.isBarLargeEnough = function (d) {
    var yCoordinate = (d.y === 0) ? 0 : this.yScale(d.length);
    return yCoordinate > 12;
};


/*
 * drawAxis()
 *
 * Draws the x-axis for the histogram.
 */
TransactionHistogram.prototype.drawAxis = function() {
    // Add the X axis


    // Add ticks for xAxis (rendering the xAxis bar and labels)
    if (this.xTicks === undefined) {
        var xAxis = d3.svg.axis()
            .scale(this.xScale)
            .orient("bottom")
            .tickFormat(d3.format('$.1s'));
        this.xTicks = this.svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + this.height + ")");
        this.xTicks.transition().duration(500).call(xAxis);
    }
};


/*
 * setScale(data)
 *
 * Sets the X and Y scales for the histogram. Based on the `data` provided.
 *
 * @params {Array} data is an array of objects with keys 'state' and 'amount'
 * @return {Array} returns histogramData so that recomputation of buckets not necesary
 */
TransactionHistogram.prototype.setScale = function (data) {
    var histogramData = this.histogramLayout(data);

    this.xScale = d3.scale.threshold()
      .domain(this.bins)
      .range(d3.range(0, this.width, this.width/(this.bins.length + 1)));


    this.yScale = d3.scale.linear()
        .domain([
            0,
            d3.max(histogramData, function (d) { return d.length })
        ])
        .range([0, this.height]);

    return histogramData;
};

/*
 * hasScaleSet()
 *
 * Checks if the scale has already been set.
 *
 * @return {boolean} true if the x and y scales have been already set.
 */
TransactionHistogram.prototype.hasScaleSet = function () {
    return this.xScale !== undefined && this.yScale !== undefined;
};

TransactionHistogram.prototype.colorStates = {
    'PRIMARY': '#fd8d13',
    'SECONDARY': '#02c971',
    'DEFAULT': '#ffb324'
};


/*
 * setBarColor()
 *
 * Set the color mode of the bar.
 */
TransactionHistogram.prototype.setHistogramColor = function (colorState) {
    this.currentColorState = colorState;
};

/*
 * widthOfBar(data)
 *
 * Computes width of histogram bar according to bin's`data` provided.
 *
 * @params {Array} data is an array of objects with keys 'state' and 'amount'
 * @return {Array} returns number - width of bar.
 */

TransactionHistogram.prototype.widthOfBar = function (data) {
    var x0 = this.xScale(data['x']);
    var x1 = this.xScale(data['dx'] + data['x']);
    return  x1 - x0;
};