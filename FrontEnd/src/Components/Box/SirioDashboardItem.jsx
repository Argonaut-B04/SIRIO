import React, { Component } from 'react';
import classes from './SirioDashboardBox.module.css';

export class SirioDashboardItem extends Component {
    render() {

        return (
            <div className={[classes.box, "col"].join(" ")}>
                <h3 className={classes.value}>{this.props.data.value}</h3>
                <h6>{this.props.data.title}</h6>
            </div>
        )
    }
}

export default SirioDashboardItem
