import React, { Component } from 'react';
import classes from './ErrorDetail.module.css';

export class ErrorDetail extends Component {
    render() {
        return (
            <div className={classes.errorDetailMainWrapper}>
                <h1 className={classes.errorTitle}>
                    {this.props.code}
                </h1>
                <h5 className={classes.errorDetail}>
                    {this.props.detail}
                </h5>
                <small>
                    Jika anda menemukan halaman ini saat menggunakan Sirio dengan baik, hubungi Developer
                </small>
            </div>
        )
    }
}

export default ErrorDetail
