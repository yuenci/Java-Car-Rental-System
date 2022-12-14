let avtivityBox = document.getElementById("right-bottom-box");

let hd1 = document.getElementById("headerData-1");
let hd2 = document.getElementById("headerData-2");
let hd3 = document.getElementById("headerData-3");

function addHeaderData(headerData) {
    let hd1Text = headerData[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    let hd2Text = headerData[1].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    let hd3Text = headerData[2].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");



    hd1.innerHTML = "RM" + hd1Text;
    hd2.innerHTML = "RM" + hd2Text;
    hd3.innerHTML = "RM" + hd3Text;
}


function addAvtivityCard(id, name, time, event) {
    let card = document.createElement("div");
    card.classList.add("activity-card");
    card.innerHTML = `
        <div><img src="../contactUs/avatar/${id}.png" class="activity-card-avatar"></div>
                    <div class="activity-card-info">
                        <p class="activity-card-name">${name}</p>
                        <p class="activity-card-time">${time}</p>
                    </div>
                    <div class="activity-card-text">${event}</div>
    `;
    avtivityBox.appendChild(card);
}
function addTableRow(name, id, date, anount) {
    let tableBody = document.getElementsByTagName("tbody")[0];
    let row = document.createElement("tr");
    row.innerHTML = `
        <td>${name}</td>
        <td>${id}</td>
        <td>${date}</td>
        <td>RM ${anount}</td>
    `;
    tableBody.appendChild(row);
}







/////// chart - line chart


function addLineChart(lineChartData) {
    var dom = document.getElementById('line-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        //set bar width
        barSize: 0.1,
        backgroundColor: '#fafafa',
        legend: {
            data: ['']
        },
        tooltip: {},
        dataset: {
            dimensions: ['product', 'Paid', 'Delivered', 'Finished'],
            source: lineChartData
        },
        xAxis: { type: 'category' },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [{
            type: 'bar',
            barWidth: 10,
            //color
            itemStyle: {
                normal: {
                    color: '#F76560'
                }
            }
        },
        {
            type: 'bar',
            barWidth: 10, itemStyle: {
                normal: {
                    color: '#1D90F4'
                }
            }
        },
        {
            type: 'bar',
            barWidth: 10, itemStyle: {
                normal: {
                    color: '#7EB712'
                }
            }
        }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}





function addPieChart(pieData) {
    var dom = document.getElementById('pie-char');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            data: ['']
        },
        series: [
            {
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: pieData
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}


/////// initData 
addHeaderData(headerData); // add header data

// let lineData = [
//     { product: 'Sun', Paid: 43.3, Delivered: 85.8, Finished: 93.7 },
//     { product: 'Mon', Paid: 83.1, Delivered: 73.4, Finished: 55.1 },
//     { product: 'Tur', Paid: 86.4, Delivered: 65.2, Finished: 82.5 },
//     { product: 'Wed', Paid: 72.4, Delivered: 53.9, Finished: 39.1 },
//     { product: 'Thu', Paid: 72.4, Delivered: 53.9, Finished: 39.1 },
//     { product: 'Fri', Paid: 72.4, Delivered: 53.9, Finished: 39.1 },
//     { product: 'Sat', Paid: 72.4, Delivered: 53.9, Finished: 39.1 }
// ]
addLineChart(lineData)

// let pieData = [
//     { value: 1048, name: 'Paid' },
//     { value: 735, name: 'Canceled' },
//     { value: 580, name: 'Delivered' },
//     { value: 484, name: 'Driving' },
//     { value: 300, name: 'Finished' }
// ]
addPieChart(pieData)


for (let i = 0; i < 5; i++) {
    addAvtivityCard(activityCardData[i][0], activityCardData[i][1], activityCardData[i][2], activityCardData[i][3])
    //addAvtivityCard(16, "Alehe Anuha", "17:33", "PaidPaid");
}

for (let i = 0; i < 5; i++) {
    addTableRow(tableData[i][0], tableData[i][1], tableData[i][2], tableData[i][3]);
    //addTableRow("Alehe Anuha", 4564166543446, "2022-01-10", 84546);
}
