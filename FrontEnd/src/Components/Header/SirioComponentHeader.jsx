import React, { Component } from 'react';
import classes from './SirioComponentHeader.module.css';

class SirioComponentHeader extends Component {
    render() {
        const { headerWrapper } = classes;
        const { title, headerButton, betweenTitleSubtitle, subtitle } = this.props;

        return (
            <div className={headerWrapper}>
                <div className="row">
                    <div className="col-12">
                        {title &&
                            <h2 className={classes.title}>
                                {title}
                            </h2>
                        }
                    </div>
                    <div className="col-6" style={{marginTop: "20px"}}>
                        {betweenTitleSubtitle}
                        {subtitle &&
                            <h5 className={classes.subtitle}>
                                {subtitle}
                            </h5>
                        }
                    </div>
                    <div className="col-6 d-flex justify-content-end align-items-center">
                        {headerButton && <div className="ml-auto">{headerButton}</div>}
                    </div>
                </div>
            </div>
        );
    }
}

export default SirioComponentHeader;
