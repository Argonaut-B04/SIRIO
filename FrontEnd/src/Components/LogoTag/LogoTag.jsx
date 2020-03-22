import React from 'react';
import classes from './LogoTag.module.css';

export default class LogoTag extends React.Component {
    render() {
        return (
            <>
                <div className={classes.logo}>
                    <img src={process.env.PUBLIC_URL + '/logo.png'} className={classes.logoImage} alt="SIRIO Logo" />
                    <h1 className={this.props.light ? classes.logoName : classes.logoNameDark}>SIRIO</h1>
                </div>
                <h4 className={classes.logoDesc}>
                    Sistem Informasi Risiko Operasional
                </h4>
            </>
        );
    }
}