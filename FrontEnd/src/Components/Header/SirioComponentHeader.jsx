import React, { Component } from 'react';
import classes from './SirioComponentHeader.module.css';

class SirioComponentHeader extends Component {
    render() {
        const { headerWrapper } = classes;
        const { title, headerButton, betweenTitleSubtitle, subtitle } = this.props;

        return (
            <div className={headerWrapper}>
                <div className="row">
                    {!!headerButton ?
                        <>
                            <div className="col-9">
                                {title &&
                                    <h2 className={classes.title}>
                                        {title}
                                    </h2>
                                }
                                {betweenTitleSubtitle}
                                {subtitle &&
                                    <h5 className={classes.subtitle}>
                                        {subtitle}
                                    </h5>
                                }
                            </div>
                            <div className="col-3 d-flex justify-content-end align-items-center">
                                {headerButton && <div className="ml-auto">{headerButton}</div>}
                            </div>
                        </>
                        :
                        <>
                            {title &&
                                <h2 className={classes.title}>
                                    {title}
                                </h2>
                            }
                            {betweenTitleSubtitle}
                            {subtitle &&
                                <h5 className={classes.subtitle}>
                                    {subtitle}
                                </h5>
                            }
                        </>
                    }
                </div>
            </div>
        );
    }
}

export default SirioComponentHeader;
