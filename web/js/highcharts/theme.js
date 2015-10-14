/**
 * Created by Syiml on 2015/7/6 0006.
 */
/**
 * Grid theme for Highcharts JS
 * @author Torstein Honsi
 */

Highcharts.theme = {
    colors: ['#000000', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(240, 240, 255)']
            ]
        },
        borderWidth: 0,
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1
    },
    title: {
        style: {
            color: '#000',
            font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    subtitle: {
        style: {
            color: '#666666',
            font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    xAxis: {
        gridLineWidth: 0,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, sans-serif'

            }
        }
    },
    yAxis: {
        //minorTickInterval: 'auto',
        gridLineWidth: 0,
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, sans-serif'
            }
        },
        plotBands: [
            {
                from: -10000,
                to: 1000,
                color: 'rgba(128, 128, 128, 0.5)'
            }, {
                from: 1000,
                to: 1200,
                color: 'rgba(64, 191, 64, 0.5)'
            }, { // Gentle breeze
                from: 1200,
                to: 1400,
                color: 'rgba(0, 255, 0, 0.5)'
            }, {
                from: 1400,
                to: 1500,
                color: 'rgba(0, 192, 255, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 1500,
                to: 1600,
                color: 'rgba(0, 0, 255, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 1600,
                to: 1700,
                color: 'rgba(192, 0, 255, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 1700,
                to: 1900,
                color: 'rgba(255, 0, 255, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 1900,
                to: 2000,
                color: 'rgba(255, 0, 128, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 2000,
                to: 2200,
                color: 'rgba(255, 0, 0, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 2200,
                to: 2600,
                color: 'rgba(255, 128, 0, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }, {
                from: 2600,
                to: 10000,
                color: 'rgba(255, 215, 0, 0.5)',
                label: {
                    text: '',
                    style: {
                        color: '#606060'
                    }
                }
            }]
    },
    //legend: {
    //    itemStyle: {
    //        font: '9pt Trebuchet MS, Verdana, sans-serif',
    //        color: 'black'
    //
    //    },
    //    itemHoverStyle: {
    //        color: '#039'
    //    },
    //    itemHiddenStyle: {
    //        color: 'gray'
    //    }
    //},
    labels: {
        style: {
            color: '#99b'
        }
    },

    navigation: {
        buttonOptions: {
            theme: {
                stroke: '#CCCCCC'
            }
        }
    }
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);