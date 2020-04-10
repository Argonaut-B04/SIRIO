import React, { Component } from 'react';
import classes from './ComponentWrapper.module.css';

export class ComponentWrapper extends Component {
    render() {
        return (
            <div className={[this.props.classes, classes.componentWrapper].join(" ")}>
                {this.props.children}
            </div>
        )
    }
}

export default ComponentWrapper
