import React, { Component } from 'react';
import classes from './SirioMainLayout.module.css';
import SirioSideNav from "../Components/SideNav/SirioSideNav";

class SirioMainLayout extends Component {
    render() {
        return (
            <>
                <SirioSideNav />
                <div className={classes.mainContent}>
                    <div className={classes.pageContent}>
                        {this.props.children}
                    </div>
                </div>
            </>
        );
    }
}

export default SirioMainLayout;
