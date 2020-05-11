import React, { Component } from 'react';
import { Bar } from 'react-chartjs-2';

export class SirioBarChart extends Component {
    render() {
        return (
            <div className={this.props.classes}>
                <Bar
                    data={this.props.data}
                    options={{
                        maintainAspectRatio: true
                    }}
                />
            </div>
        )
    }
}

export default SirioBarChart
