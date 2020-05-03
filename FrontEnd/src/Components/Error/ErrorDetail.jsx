import React, { Component } from 'react';
import classes from './ErrorDetail.module.css';

export default class ErrorDetail extends Component {
    render() {
        const { code, detail } = this.props;
        const { errorDetailMainWrapper, errorTitle, errorDetail } = classes;
        return (
            <div className={errorDetailMainWrapper}>
                <h1 className={errorTitle}>
                    {code}
                </h1>
                <h5 className={errorDetail}>
                    {detail}
                </h5>
                <small>
                    Jika anda menemukan halaman ini saat menggunakan Sirio dengan benar, hubungi Developer
                </small>
            </div>
        )
    }
}