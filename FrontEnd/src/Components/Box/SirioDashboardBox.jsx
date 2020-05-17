import React, { Component } from 'react';
import SirioDashboardItem from './SirioDashboardItem';

export class SirioDashboardBox extends Component {
    render() {
        return (
            <div className=" my-5 d-flex justify-content-center align-items-center row">
                {this.props.data.map((entry, idx) => <SirioDashboardItem data={entry} key={idx} />)}
            </div>
        )
    }
}

export default SirioDashboardBox
