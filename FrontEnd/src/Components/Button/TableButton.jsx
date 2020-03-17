import React from 'react';
import classes from './TableButton.module.css';

export default class TableButton extends React.Component {

    render() {
        let theClass = classes.button;
        if (this.props.disabled) {
            theClass = [theClass, classes.disabled].join(' ');
        }
        if (this.props.recommended) {
            theClass = classes.recommendedBtn
        }
        return (
            <button
                onClick={() => window.location.href = this.props.link}
                className={theClass}
                recommended={this.props.recommended}
            >
                <h6 className={classes.buttonTitle}>
                    {this.props.children}
                </h6>
            </button>
        )
    }
}